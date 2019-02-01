package com.kafka.service;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.processor.*;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.Stores;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * 低级别处理器API
 * Created by Administrator on 2018/7/17.
 * 字数统计
 */
@Component
@Order(value = 1)
public class WordCountProcessorDemo implements ApplicationRunner {


    static final String APPLICATION_ID_CONFIG = "wordcount-processor";

    @Value("${spring.kafka.bootstrap-servers}")
    private String DEFAULT_BOOTSTRAP_SERVERS;

    /**
     * 处理器
     */
    private static class MyProcessorSupplier
            implements ProcessorSupplier<String, String>
    {

        @Override
        public Processor<String, String> get() {
            return new Processor<String, String>() {

                private ProcessorContext context;//执行器上下文
                private KeyValueStore<String,Integer> kvStore;//key和value的存储容器

                @Override
                public void init(final ProcessorContext context) {

                    this.context = context;
                    //执行周期 每1秒
                    this.context.schedule(1000L, PunctuationType.STREAM_TIME, new Punctuator(){
                        public void punctuate(long timestamp)
                        {
                            KeyValueIterator<String, Integer> iter = kvStore.all();
                            Throwable localThrowable2 = null;
                            try
                            {
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                String str = df.format(timestamp);
                                System.out.println("----------- " + str + " ----------- ");
                                while (iter.hasNext())
                                {
                                    KeyValue<String, Integer> entry = (KeyValue)iter.next();

                                    System.out.println("[" + (String)entry.key + ", " + entry.value + "]");

                                    context.forward(entry.key, ((Integer)entry.value).toString());
                                }
                            }
                            catch (Throwable localThrowable1)
                            {
                                localThrowable2 = localThrowable1;throw localThrowable1;
                            }
                            finally
                            {
                                if (iter != null) {
                                    if (localThrowable2 != null) {
                                        try
                                        {
                                            iter.close();
                                        }
                                        catch (Throwable x2)
                                        {
                                            localThrowable2.addSuppressed(x2);
                                        }
                                    } else {
                                        iter.close();
                                    }
                                }
                            }
                        }
                    });
                    this.kvStore = ((KeyValueStore)context.getStateStore("Counts"));
                }

                /**
                 * 处理逻辑
                 * 每个接收一个记录，将字符串的值分割成单词，并更新他们的数量到状态存储
                 * @param dummy
                 * @param line
                 */
                @Override
                public void process(String dummy, String line) {
                    String[] words = line.toLowerCase(Locale.getDefault()).split(" ");
                    for (String word : words)
                    {
                        Integer oldValue = (Integer)this.kvStore.get(word);
                        if (oldValue == null) {
                            this.kvStore.put(word, Integer.valueOf(1));
                        } else {
                            this.kvStore.put(word, Integer.valueOf(oldValue.intValue() + 1));
                        }
                    }
                    this.context.commit();
                }

                /**
                 * 迭代本地状态仓库并发送总量数到下游的处理器，并提交当前的流状态
                 * @param timestamp
                 */
                @Override
                public void punctuate(long timestamp) {
                }

                @Override
                public void close() {
                }
            };
        }
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        Properties props = new Properties();
        props.put("application.id", APPLICATION_ID_CONFIG);
        props.put("bootstrap.servers", DEFAULT_BOOTSTRAP_SERVERS);
        props.put("cache.max.bytes.buffering", Integer.valueOf(0));
        props.put("default.key.serde", Serdes.String().getClass());
        props.put("default.value.serde", Serdes.String().getClass());
        props.put("auto.offset.reset", "earliest");

        Topology builder = new Topology();
        builder.addSource("Source", new String[] { "streams-plaintext-input" });//输入源
        builder.addProcessor("Process", new MyProcessorSupplier(), new String[] { "Source" });//执行器
        builder.addStateStore(
                Stores.keyValueStoreBuilder(
                        Stores.persistentKeyValueStore("Counts"),
                        Serdes.String(),
                        Serdes.Integer()
                ),
                new String[] { "Process" });//存储
        builder.addSink("Sink", "streams-wordcount-processor-output", new String[] { "Process" });//输出

        final KafkaStreams streams = new KafkaStreams(builder, props);
//        final CountDownLatch latch = new CountDownLatch(1);
//
//
//        Runtime.getRuntime().addShutdownHook(new Thread("streams-wordcount-shutdown-hook")
//        {
//            public void run()
//            {
//                streams.close();
//                latch.countDown();
//            }
//        });
        try
        {
            streams.start();
//            latch.await();
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Object object = "";
        if(!StringUtils.isEmpty(object)){
            System.out.println("1");
        }else {
            System.out.println("2");
        }
    }
}
