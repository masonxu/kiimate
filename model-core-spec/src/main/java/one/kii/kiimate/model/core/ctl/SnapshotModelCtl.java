package one.kii.kiimate.model.core.ctl;

import one.kii.kiimate.model.core.api.SnapshotModelApi;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.receiver.ErestResponse;
import one.kii.summer.io.receiver.WriteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.model.core.ctl.SnapshotModelCtl.OWNER_ID;

/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping("/api/v1/{" + OWNER_ID + "}/commit")
@CrossOrigin(origins = "*")
public class SnapshotModelCtl extends WriteController {

    public static final String OWNER_ID = "ownerId";

    @Autowired
    private SnapshotModelApi api;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<SnapshotModelApi.Receipt> commitForm(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @ModelAttribute SnapshotModelApi.Form form) {
        return commit(requestId, operatorId, ownerId, form);
    }


    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SnapshotModelApi.Receipt> commitJson(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @RequestBody SnapshotModelApi.Form form) {
        return commit(requestId, operatorId, ownerId, form);
    }

    private ResponseEntity<SnapshotModelApi.Receipt> commit(@RequestHeader(ErestHeaders.REQUEST_ID) String requestId, @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId, @PathVariable(OWNER_ID) String ownerId, @ModelAttribute SnapshotModelApi.Form form) {
        try {
            WriteContext context = buildContext(requestId, operatorId, ownerId);
            return ErestResponse.created(requestId, api.commit(context, form));
        } catch (BadRequest badRequest) {
            return ErestResponse.badRequest(requestId, badRequest.getFields());
        } catch (Conflict conflict) {
            return ErestResponse.conflict(requestId, conflict.getKeys());
        }
    }


}
