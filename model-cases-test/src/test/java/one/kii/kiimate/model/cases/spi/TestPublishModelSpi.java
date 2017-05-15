package one.kii.kiimate.model.cases.spi;

import one.kii.summer.io.exception.Panic;
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
@ComponentScan("com.sinewang.kiimate.model")
@SpringBootTest(classes = {TestPublishModelSpi.class})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class TestPublishModelSpi {

    @Autowired
    private PublishModelSpi publishModelSpi;

    @Autowired
    private VisitExtensionSpi visitExtensionSpi;

    private String group = "testGroup";


    @Test
    public void test() {
        PublishModelSpi.Form form = new PublishModelSpi.Form();
        form.setGroup(group);
        form.setKlass(ThisIsASpringBootConfiguration.class);
        PublishModelSpi.Receipt receipt = null;
        try {
            receipt = publishModelSpi.commit(form);
        } catch (Panic panic) {
            panic.printStackTrace();
        }

        Assert.assertNotNull(receipt);

        VisitExtensionSpi.GroupForm form2 = new VisitExtensionSpi.GroupForm();

        form2.setGroup(group);

        String extensionJson = null;
        try {
            extensionJson = visitExtensionSpi.visit(form2);
        } catch (Panic panic) {
            panic.printStackTrace();
        }

        Assert.assertNotNull(extensionJson);
    }

    class ThisIsASpringBootConfiguration {

        public Spring spring;

        public Server server;

        public Logging logging;

    }

    class Spring {
        DataSource datasource;
        Profiles profiles;
    }

    class Profiles {
        String active;
    }


    class DataSource {

        public String driverClassName;

        public String url;

        public String username;

        public String password;
    }

    class Server {
        public int port;
    }

    class Logging {
        public String level;
    }


}