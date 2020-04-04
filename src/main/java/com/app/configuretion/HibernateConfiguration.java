package com.app.configuretion;

import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.InlineShardingStrategyConfiguration;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class HibernateConfiguration {

    private String driverClassName = "org.h2.Driver";
    private String url0 = "jdbc:h2:~/../../Data/IdeaProjects/test_tasks/JandexMoney/money0";
    private String url1 = "jdbc:h2:~/../../Data/IdeaProjects/test_tasks/JandexMoney/money1";
    private String url2 = "jdbc:h2:~/../../Data/IdeaProjects/test_tasks/JandexMoney/money2";

    @Bean
    public SessionFactory sessionFactory() {
        return new LocalSessionFactoryBuilder(dataSource()).scanPackages("com.app.model").
                addProperties(hibernateProperties()).buildSessionFactory();
    }

    @Bean
    public DataSource dataSource() {
        // To configure the actual data source
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        // To configure the first data source
        SimpleDriverDataSource dataSource0 = new SimpleDriverDataSource();
        try {
            dataSource0.setDriverClass((Class<? extends Driver>) Class.forName(driverClassName));
            dataSource0.setUrl(url0);
            dataSourceMap.put("ds_0", dataSource0);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        // To configure the second data source
        SimpleDriverDataSource dataSource1 = new SimpleDriverDataSource();
        try {
            dataSource1.setDriverClass((Class<? extends Driver>) Class.forName(driverClassName));
            dataSource1.setUrl(url1);
            dataSourceMap.put("ds_1", dataSource1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        // To configure the second data source
        SimpleDriverDataSource dataSource2 = new SimpleDriverDataSource();
        try {
            dataSource1.setDriverClass((Class<? extends Driver>) Class.forName(driverClassName));
            dataSource1.setUrl(url2);
            dataSourceMap.put("ds_2", dataSource2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        // To configure the table rules for Payment
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration();
        orderTableRuleConfig.setLogicTable("t_payment");
        orderTableRuleConfig.setActualDataNodes("ds_${0..1}.t_payment_${0..1}");

        // To configure the strategy for database Sharding.
        orderTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "ds_${id % 3}"));

        // To configure the strategy for table Sharding.
        orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "t_payment_${payment_id % 3}"));

        // To configure the strategy rule of Sharding
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);

        try {
            DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new ConcurrentHashMap(), hibernateProperties());
            return dataSource;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean
    public Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.show_sql", "hibernate.show_sql");
        properties.put("hibernate.format_sql", "hibernate.format_sql");
        return properties;
    }
}
