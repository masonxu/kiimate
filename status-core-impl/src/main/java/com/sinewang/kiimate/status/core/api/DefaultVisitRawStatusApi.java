package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.VisitRawStatusApi;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.fui.InstanceTransformer;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@Component
public class DefaultVisitRawStatusApi implements VisitRawStatusApi {

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private InstanceTransformer instanceTransformer;

    @Override
    public Map<String, Object> visit(ReadContext context, GroupNameTreeForm form) throws NotFound {
        ModelSubscriptionDai.ChannelGroupNameTree channel = ValueMapping.from(ModelSubscriptionDai.ChannelGroupNameTree.class, form, context);

        ModelSubscriptionDai.ModelSubscription subscription = modelSubscriptionDai.selectSubscription(channel);

        ModelSubscriptionDai.ChannelSubId subId = ValueMapping.from(ModelSubscriptionDai.ChannelSubId.class, context, subscription);

        ModelSubscriptionDai.ModelPubSet modelPubSet = modelSubscriptionDai.getModelPubSetByOwnerSubscription(subId);

        InstanceDai.ChannelStatusId statusId = ValueMapping.from(InstanceDai.ChannelStatusId.class, subscription);
        List<InstanceDai.Instance> instances = instanceDai.loadInstances(statusId);

        return instanceTransformer.toRawValue(instances, modelPubSet);
    }

}
