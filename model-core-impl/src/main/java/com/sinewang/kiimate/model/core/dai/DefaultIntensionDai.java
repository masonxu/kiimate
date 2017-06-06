package com.sinewang.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.IntensionMapper;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.summer.beans.utils.ConflictFinder;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@Component
public class DefaultIntensionDai implements IntensionDai {


    @Autowired
    private IntensionMapper intensionMapper;

    @Override
    public void remember(Record record) throws Conflict, BadRequest {
        NotBadRequest.from(record);
        Map<String, Object> conflicts = ConflictFinder.find(record);
        Record oldRecord = intensionMapper.selectIntensionByCommit(record.getCommit());
        if (oldRecord != null) {
            throw new Conflict(conflicts.keySet().toArray(new String[0]));
        }
        intensionMapper.insertIntension(
                record.getId(),
                record.getCommit(),
                record.getExtId(),
                record.getField(),
                record.getSingle(),
                record.getStructure(),
                record.getRefPubSet(),
                record.getVisibility(),
                record.getRequired(),
                record.getOperatorId(),
                record.getBeginTime()
        );

    }

    @Override
    public List<Record> load(ChannelLatestExtension channel) throws BadRequest, Panic {
        NotBadRequest.from(channel);
        List<Record> records = intensionMapper.selectLatestIntensionsByExtId(channel.getId());
        if (records.isEmpty()) {
            throw new BadRequest("id");
        }
        return records;
    }


    @Override
    public List<Record> loadLast(ChannelLastExtension channel) throws BadRequest {
        NotBadRequest.from(channel);
        if (channel.getBeginTime() == null) {
            return intensionMapper.selectLatestIntensionsByExtId(channel.getId());
        } else {
            return intensionMapper.selectLastIntensionsByExtId(
                    channel.getId(),
                    channel.getBeginTime());
        }
    }

    @Override
    public List<Record> loadLast(ChannelPubSet channel) throws BadRequest {
        NotBadRequest.from(channel);
        List<String> fields = intensionMapper.selectLastFieldsByExtIdPubSet(
                channel.getExtId(),
                channel.getPubSet(),
                channel.getBeginTime());
        List<Record> records = new ArrayList<>();
        for (String field : fields) {
            Record record = intensionMapper.selectLastIntensionByExtIdField(
                    channel.getExtId(),
                    field);
            records.add(record);
        }
        return records;
    }


    @Override
    public void forget(ChannelId channel) throws BadRequest {
        NotBadRequest.from(channel);
        Date now = new Date();
        intensionMapper.updateLatestIntensionEndTimeById(
                channel.getId(),
                now);
    }
}
