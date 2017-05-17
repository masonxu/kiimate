package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.VisitIntensionsApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.fui.AnExtensionExtractor;
import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@Component
public class DefaultVisitIntensionsApi implements VisitIntensionsApi {

    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private AnExtensionExtractor extensionExtractor;

    @Autowired
    private ExtensionDai extensionDai;


    @Override
    public Receipt visit(ReadContext context, Form form) throws NotFound {

        AnExtensionExtractor.Extension extension = DataTools.copy(form, AnExtensionExtractor.Extension.class);
        extension.setOwnerId(context.getOwnerId());
        extension.setVisibility(VISIBILITY_PUBLIC);
        extensionExtractor.hashId(extension);

        String extId = extension.getId();

        ExtensionDai.Extension dbExtension = extensionDai.selectExtensionById(extId);

        if (dbExtension == null) {
            throw new NotFound(new String[]{context.getOwnerId(), form.getGroup(), form.getName(), form.getTree()});
        }

        List<IntensionDai.Intension> list = intensionDai.selectIntensionsByExtId(extId);

        List<Intension> intensions = DataTools.copy(list, Intension.class);

        Receipt receipt = DataTools.copy(extension, Receipt.class);
        receipt.setIntensions(intensions);
        return receipt;
    }
}
