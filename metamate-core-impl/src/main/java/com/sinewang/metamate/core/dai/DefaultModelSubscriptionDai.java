package com.sinewang.metamate.core.dai;

import com.sinewang.metamate.core.dai.mapper.ModelSubscriptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wang.yanjiong.metamate.core.dai.ModelSubscriptionDai;

import java.util.Date;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@Component
public class DefaultModelSubscriptionDai implements ModelSubscriptionDai {


    @Autowired
    private ModelSubscriptionMapper modelSubscriptionMapper;


    @Override
    public void save(ModelSubscription modelSubscription) {
        Date now = new Date();
        modelSubscriptionMapper.insertSubscription(
                modelSubscription.getId(),
                modelSubscription.getProviderId(),
                modelSubscription.getExtId(),
                modelSubscription.getPublication(),
                modelSubscription.getVersion(),
                modelSubscription.getSubscriberId(),
                modelSubscription.getGroup(),
                modelSubscription.getName(),
                modelSubscription.getTree(),
                modelSubscription.getOperatorId(),
                now
        );
    }

    @Override
    public ModelSubscription getLatestSubscriptionBySubscriberIdGroupNameTree(String subscriberId, String group, String name, String tree) {
        return modelSubscriptionMapper.selectLatestSubscriptionBySubscriberIdGroupNameTree(
                subscriberId, group, name, tree
        );
    }

    @Override
    public void deleteById(String id) {
        modelSubscriptionMapper.deleteById(id);
    }


}