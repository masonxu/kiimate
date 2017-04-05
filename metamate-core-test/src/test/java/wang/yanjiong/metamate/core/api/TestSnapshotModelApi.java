package wang.yanjiong.metamate.core.api;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by WangYanJiong on 05/04/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ComponentScan("com.sinewang.metamate.core")
@SpringBootTest(classes = {TestDeclareExtensionApi.class})
public class TestSnapshotModelApi {

    @Autowired
    private SnapshotModelApi snapshotModelApi;

    @Test
    public void test() {
        SnapshotModelApi.SnapshotModelForm form = new SnapshotModelApi.SnapshotModelForm();
        form.setVersion("1.0.0");
        ResponseEntity<SnapshotModelApi.SnapshotModelReceipt> response = snapshotModelApi.snapshot(
                form,
                "wangyj",
                "testOperatorId",
                "one.euler.gitlab",
                "project",
                "master"

        );
        SnapshotModelApi.SnapshotModelReceipt receipt = response.getBody();
        Assert.assertNotNull(receipt);
    }
}