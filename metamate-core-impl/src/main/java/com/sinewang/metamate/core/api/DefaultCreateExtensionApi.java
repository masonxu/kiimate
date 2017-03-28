package com.sinewang.metamate.core.api;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.magnet.xi.boundary.util.ResponseUtil;
import wang.yanjiong.metamate.core.api.CreateExtensionApi;
import wang.yanjiong.metamate.core.dai.ExtensionDai;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;
import wang.yanjiong.metamate.core.fi.AnStructureValidator;
import wang.yanjiong.metamate.core.fi.AnVisibilityValidator;

/**
 * Created by WangYanJiong on 3/24/17.
 */
@RestController
public class DefaultCreateExtensionApi implements CreateExtensionApi {


    @Autowired
    private ExtensionDai extensionDai;

    @Autowired
    private AnExtensionExtractor extensionExtractor;

    @Autowired
    private AnStructureValidator structureValidator;

    @Autowired
    private AnVisibilityValidator visibilityValidator;

    @Override
    public Receipt createExtensionViaFormUrlEncoded(Form form) {

        AnExtensionExtractor.Extension extension = null;
        try {
            extension = extensionExtractor.extract(form);
        } catch (AnExtensionExtractor.MissingParamException e) {
            return ResponseUtil.rejected(form, Receipt.class, e.getMessage());
        }

        boolean isValidStructure = structureValidator.isValid(extension.getStructure());
        if (!isValidStructure) {
            return ResponseUtil.rejected(form, Receipt.class, extension);
        }

        boolean isValidVisibility = visibilityValidator.isValid(extension.getVisibility());
        if (!isValidVisibility) {
            return ResponseUtil.rejected(form, Receipt.class, extension);
        }

        ExtensionDai.Extension daiRecord = new ExtensionDai.Extension();
        BeanUtils.copyProperties(extension, daiRecord);

        try {
            extensionDai.insertExtension(daiRecord);
            return ResponseUtil.accepted(form, Receipt.class, extension);
        } catch (ExtensionDai.ExtensionDuplicated extensionDuplicated) {
            return ResponseUtil.rejected(form, Receipt.class, extension);
        }


    }

    @Override
    public Receipt createExtensionViaJson(Form form) {
        return null;
    }
}