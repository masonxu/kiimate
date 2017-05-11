package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.RefreshStatusApi;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.receiver.ErestResponse;
import one.kii.summer.io.receiver.WriteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.status.core.ctl.RefreshStatusCtl.OWNER_ID;
import static one.kii.kiimate.status.core.ctl.RefreshStatusCtl.SUB_ID;


/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping(value = "/api/v1/{" + OWNER_ID + "}/status/{" + SUB_ID + "}", method = RequestMethod.PUT)
public class RefreshStatusCtl extends WriteController {

    public static final String OWNER_ID = "ownerId";

    public static final String SUB_ID = "subId";

    @Autowired
    private RefreshStatusApi api;

    @RequestMapping
    ResponseEntity<RefreshStatusApi.Receipt> commit(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(SUB_ID) String subId,
            @RequestParam MultiValueMap<String, String> map) {
        try {

            WriteContext context = buildContext(requestId, operatorId, ownerId);

            RefreshStatusApi.Form form = new RefreshStatusApi.Form();
            form.setSubId(subId);
            form.setMap(map);

            return ErestResponse.created(requestId, api.commit(context, form));
        } catch (NotFound notFound) {
            return ErestResponse.notFound(requestId, notFound.getKey());
        } catch (Conflict conflict) {
            return ErestResponse.conflict(requestId, conflict.getKeys()[0]);
        }
    }

}