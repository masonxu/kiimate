package wang.yanjiong.metamate.core.fi;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface AnInstanceExtractor {

    List<Instance> extract(String owner, String group, String name, String version, String ownerId, String operatorId, Map<String, String[]> map);

    @Data
    class Instance {

        private String id;

        private String extId;

        private String intId;

        private String ownerId;

        private String operatorId;

        private String field;

        private String[] values;

    }
}
