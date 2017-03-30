package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.magnet.xi.boundary.Response;

import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
@RestController
@RequestMapping("/v1")
public interface GetIntensionsApi {

    @RequestMapping(value = "/intensions/{extId}", method = RequestMethod.GET)
    Receipt readIntensionsByExiId(@PathVariable("extId") String extId);

    @RequestMapping(value = "/intensions/{group}/{name}/{tree.+}", method = RequestMethod.GET)
    Receipt readIntensionsByGroupNameVersion(@PathVariable("group") String group,
                                      @PathVariable("name") String name,
                                      @PathVariable("tree") String tree);


    @Data
    @EqualsAndHashCode(callSuper=false)
    class Receipt extends Response {

        private String extId;

        private List<Intension> intensions;
    }

    @Data
    @EqualsAndHashCode(callSuper=false)
    class Intension {

        private String id;

        private String field;

        private boolean single;

        private String structure;

        private String visibility;
    }

}
