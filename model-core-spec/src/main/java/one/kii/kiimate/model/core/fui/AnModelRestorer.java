package one.kii.kiimate.model.core.fui;


import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.summer.io.exception.NotFound;

import java.util.Map;

/**
 * Created by WangYanJiong on 08/04/2017.
 */
public interface AnModelRestorer {

    Map<String, Object> restoreAsMetaData(IntensionDai.ChannelLastExtension channel) throws NotFound;

    Map<String, Object> restoreAsMetaData(IntensionDai.ChannelLatestExtension channel) throws NotFound;

    Map<String, IntensionDai.Record> restoreAsIntensionDict(IntensionDai.ChannelLastExtension channel) throws NotFound;
}
