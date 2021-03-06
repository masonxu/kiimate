package one.kii.kiimate.status.core.dai;

import lombok.Data;
import one.kii.summer.beans.annotations.Unique;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.zoom.OutsideView;
import one.kii.summer.zoom.ZoomInById;
import one.kii.summer.zoom.ZoomOutBySet;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 27/05/2017.
 */
public interface GlimpsesDai {

    List<Providers> queryProviders(ClueId clue) throws BadRequest;

    Publication load(ZoomOutBySet channel) throws Panic;

    Publication load(ZoomInById channel) throws Panic;

    @Transactional
    void remember(Glimpse subscription) throws BadRequest;

    @Transactional
    void remember(Publication publication, List<Entry> entries) throws Conflict;


    List<Publication> queryPublications(ClueGroup clue) throws BadRequest, Panic;

    List<OutsideView> queryGlimpses(ClueGroup clue) throws BadRequest, Panic;

    @Data
    class Publication {

        String set;

        @Unique
        String providerId;

        @Unique
        String modelSubId;

        @Unique
        String group;

        @Unique
        String name;

        @Unique
        String version;

        @Unique
        String visibility;

        @Unique
        String stability;

        String operatorId;

        Date beginTime;

        @MayHave
        Date endTime;
    }

    @Data
    class Entry {

        String id;

        String insId;
    }


    @Data
    class Glimpse {
        String id;

        @Unique
        String subscriberId;

        @Unique
        String set;

        String operatorId;

        Date beginTime;

        @MayHave
        Date endTime;
    }

    @Data
    class Subscribers {
        String id;
    }

    @Data
    class ClueId {
        String id;
    }

    @Data
    class Providers {
        String id;
    }


    @Data
    class ClueGroup {
        String ownerId;
        String group;
    }

}
