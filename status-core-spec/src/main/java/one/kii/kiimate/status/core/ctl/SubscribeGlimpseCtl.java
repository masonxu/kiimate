package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.SubscribeGlimpseApi;
import one.kii.summer.asdf.api.CommitApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.receiver.WriteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.status.core.ctl.PublishStatusCtl.OWNER_ID;


/**
 * Created by WangYanJiong on 22/05/2017.
 */

@RestController
@RequestMapping("/api/v1/{" + OWNER_ID + "}/subscriptions/glimpse")
@CrossOrigin(origins = "*")
public class SubscribeGlimpseCtl extends WriteController {

    public static final String OWNER_ID = "owner-id";


    @Autowired
    private SubscribeGlimpseApi api;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<SubscribeGlimpseApi.Receipt> commitForm(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @ModelAttribute SubscribeGlimpseApi.Form form) {
        return commit(requestId, ownerId, operatorId, form);
    }


    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SubscribeGlimpseApi.Receipt> commitJson(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @RequestBody SubscribeGlimpseApi.Form form) {
        return commit(requestId, ownerId, operatorId, form);
    }

    private ResponseEntity<SubscribeGlimpseApi.Receipt> commit(
            String requestId,
            String operatorId,
            String ownerId,
            SubscribeGlimpseApi.Form form) {
        WriteContext context = buildContext(requestId, operatorId, ownerId);
        return CommitApiCaller.sync(api, context, form);
    }
}