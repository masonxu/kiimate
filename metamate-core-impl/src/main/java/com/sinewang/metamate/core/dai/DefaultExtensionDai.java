package com.sinewang.metamate.core.dai;

import com.sinewang.metamate.core.dai.mapper.ExtensionMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.yanjiong.metamate.core.dai.ExtensionDai;
import wang.yanjiong.metamate.core.model.Extension;

/**
 * Created by WangYanJiong on 3/23/17.
 */

@Service
public class DefaultExtensionDai implements ExtensionDai {

    @Autowired
    private ExtensionMapper extensionMapper;

    @Override
    public wang.yanjiong.metamate.core.model.Extension selectExtensionById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new NullPointerException("id is EMPTY");
        }
        Extension extension = extensionMapper.selectExtensionById(id);
        return extension;
    }

    @Override
    public void insertExtension(Extension extension) {
        extensionMapper.insertExtension(
                extension.getId(),
                extension.getGroup(),
                extension.getName(),
                extension.getVersion(),
                extension.getVisibility(),
                extension.getDataStructure()
        );
    }
}
