package hbi.sal.controllers;

import com.hand.hap.excel.annotation.ExcelExport;
import hbi.demo.dto.DemoBank;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import hbi.sal.dto.OmOrderHeaders;
import hbi.sal.service.IOmOrderHeadersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import java.util.List;

    @Controller
    public class OmOrderHeadersController extends BaseController{

    @Autowired
    private IOmOrderHeadersService service;


    @RequestMapping(value = "/hap/om/order/headers/query")
    @ExcelExport(table = OmOrderHeaders.class)
    @ResponseBody
    public ResponseData query(Long inventoryItemId,OmOrderHeaders dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.myQuery(inventoryItemId,requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/hap/om/order/headers/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<OmOrderHeaders> dto, BindingResult result, HttpServletRequest request){
       /* getValidator().validate(dto, result);*/
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.myBatchSubmit(requestCtx, dto));
       /* return new ResponseData(service.batchUpdate(requestCtx, dto));*/
    }

        @RequestMapping(value = "/hap/om/order/headers/remove")
        @ResponseBody
        public ResponseData delete(HttpServletRequest request, @RequestBody List<OmOrderHeaders> dto) throws Exception {
            IRequest requestCtx = createRequestContext(request);
            service.myDelete(requestCtx,dto);
            return new ResponseData();
        }
    }