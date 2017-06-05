package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 20/05/2017.
 */
public interface SearchAssetsApi {


    List<Assets> search(ReadContext context, QueryForm form) throws BadRequest;

    @Data
    class Assets {
        String id;

        String ownerId;

        String group;

        String name;

        String stability;

        String version;

        Date beginTime;

        @MayHave
        Date endTime;
    }

    @Data
    class QueryForm {
        String ownerId;
        String group;
    }

}