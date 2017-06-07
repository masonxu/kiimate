package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.model.core.fui.AnModelRestorer;
import one.kii.kiimate.status.core.api.RefreshStatusApi;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.fui.AnInstanceExtractor;
import one.kii.kiimate.status.core.fui.InstanceTransformer;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadResponse;
import one.kii.summer.xyz.VisitUpInsight;
import one.kii.summer.xyz.VisitUpWithId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@Component
public class DefaultRefreshStatusApi implements RefreshStatusApi {

    private static final Logger logger = LoggerFactory.getLogger(DefaultRefreshStatusApi.class);

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private AnInstanceExtractor instanceExtractor;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private AnModelRestorer modelRestorer;

    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private InstanceTransformer instanceTransformer;

    @Override
    public Receipt commit(WriteContext context, SubIdForm form) throws BadRequest, Conflict, NotFound, Panic {
        VisitUpWithId channel = ValueMapping.from(VisitUpWithId.class, context, form);

        VisitUpInsight model = modelSubscriptionDai.getModelPubSetByStatusId(channel);


        IntensionDai.ChannelLastExtension lastExtension = new IntensionDai.ChannelLastExtension();
        lastExtension.setId(model.getRootId());
        lastExtension.setBeginTime(model.getBeginTime());

        Map<String, IntensionDai.Record> dict = modelRestorer.restoreAsIntensionDict(lastExtension);

        List<InstanceDai.Instance> instances = instanceExtractor.extract(context, form, dict);

        for (InstanceDai.Instance instance : instances) {
            instance.setSubId(channel.getId());
        }
        instanceDai.remember(instances);


        IntensionDai.ChannelLastExtension rootExtension = ValueMapping.from(IntensionDai.ChannelLastExtension.class, model);


        rootExtension.setId(model.getRootId());


        VisitUpWithId id = ValueMapping.from(VisitUpWithId.class, channel);
        id.setSubscriberId(context.getOwnerId());
        List<InstanceDai.Record> newRecords = instanceDai.loadInstances(id);

        List<IntensionDai.Record> recordList = intensionDai.loadLast(rootExtension);
        List<Intension> intensions = ValueMapping.from(Intension.class, recordList);

        Map<String, Object> map = instanceTransformer.toTimedValue(newRecords, model);

        Receipt receipt = ValueMapping.from(Receipt.class, form, context);
        receipt.setMap(map);
        receipt.setIntensions(intensions);

        return NotBadResponse.of(receipt);
    }

}
