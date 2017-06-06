package one.kii.kiimate.model.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.asdf.xi.CommitApi;
import one.kii.summer.beans.annotations.ConflictFactor;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;

import java.util.List;

/**
 * Created by WangYanJiong on 4/5/17.
 */

public interface PublishModelApi extends CommitApi<PublishModelApi.Receipt, WriteContext, PublishModelApi.Form> {


    Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict, NotFound, Panic;

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Form {

        @ConflictFactor
        private String providerId;

        @ConflictFactor
        private Long extId;

        @ConflictFactor
        private String stability;

        @ConflictFactor
        private String version;

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Receipt {

        List<Intension> intensions;
        private String pubSet;
        private String providerId;

        private String ownerId;

        private String version;

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Intension {

        String field;

        Boolean single;
    }


}
