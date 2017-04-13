package wang.yanjiong.metamate.core.ctl;

import one.kii.summer.context.exception.BadRequest;
import one.kii.summer.context.exception.Conflict;
import one.kii.summer.erest.ErestHeaders;
import one.kii.summer.erest.ErestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.SnapshotModelApi;

/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping("/v1")
public class SnapshotModelCtl {

    @Autowired
    private SnapshotModelApi api;


    @RequestMapping(value = "/{ownerId}/snapshot/{group:.+}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<SnapshotModelApi.Receipt> snapshot(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @ModelAttribute SnapshotModelApi.Form form) {
        try {
            return ErestResponse.created(requestId, api.snapshot(requestId, operatorId, ownerId, group, form));
        } catch (BadRequest badRequest) {
            return ErestResponse.badRequest(requestId, badRequest.getMessage());
        } catch (Conflict conflict) {
            return ErestResponse.badRequest(requestId, conflict.getKey());
        }
    }
}
