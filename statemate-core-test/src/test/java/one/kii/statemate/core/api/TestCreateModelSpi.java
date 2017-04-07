package one.kii.statemate.core.api;

import one.kii.statemate.core.spi.CreateModelSpi;
import one.kii.statemate.core.spi.ReadExtensionSpi;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Created by WangYanJiong on 4/7/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ComponentScan("com.sinewang.statemate")
@SpringBootTest(classes = {TestCreateModelSpi.class})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class TestCreateModelSpi {

    @Autowired
    private CreateModelSpi createModelSpi;

    @Autowired
    private ReadExtensionSpi readExtensionSpi;

    private String group = "SpringBootBasicConfig";


    @Test
    public void test() {
        CreateModelSpi.Receipt receipt = createModelSpi.createModel(group, ThisIsASpringBootConfiguration.class);

        Assert.assertNotNull(receipt);

        ReadExtensionSpi.GroupForm form = new ReadExtensionSpi.GroupForm();

        form.setGroup(group);

        String extensionJson = readExtensionSpi.readMasterExtension(form);

        Assert.assertNotNull(extensionJson);
    }

    class ThisIsASpringBootConfiguration {

        public DataSource datasource;

        public Server server;

        public Logging logging;

    }

    static class DataSource {

        public String driverClassName;

        public String url;

        public String username;

        public String password;
    }

    static class Server {
        public int port;
    }

    static class Logging {
        public String level;
    }


}
