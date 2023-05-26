package vn.edu.vnua.qlsvfita.controller.admin;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnua.qlsvfita.controller.BaseController;
import vn.edu.vnua.qlsvfita.model.dto.TermListDTO;
import vn.edu.vnua.qlsvfita.model.entity.Term;
import vn.edu.vnua.qlsvfita.request.admin.term.CreateTermRequest;
import vn.edu.vnua.qlsvfita.request.admin.term.GetTermListRequest;
import vn.edu.vnua.qlsvfita.request.admin.term.UpdateTermRequest;
import vn.edu.vnua.qlsvfita.service.admin.TermService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin/term")
@RequiredArgsConstructor
public class TermController extends BaseController {
    private final TermService termService;

    private final ModelMapper modelMapper = new ModelMapper();

    @PostMapping("list")
    @PreAuthorize("hasAnyAuthority('GET_TERM_LIST')")
    public ResponseEntity<?> getTermList(@RequestBody GetTermListRequest request){
        Page<Term> page = termService.getTermList(request);
        List<TermListDTO> response = page.getContent().stream().map(
                term -> modelMapper.map(term, TermListDTO.class)
        ).collect(Collectors.toList());
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);

    }

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('CREATE_TERM')")
    public ResponseEntity<?> createTerm(@Valid @RequestBody CreateTermRequest request){
        TermListDTO response = modelMapper.map(termService.createTerm(request), TermListDTO.class);
        return buildItemResponse(response);
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_TERM')")
    public ResponseEntity<?> updateTerm(@Valid @RequestBody UpdateTermRequest request,@PathVariable String id){
        TermListDTO response = modelMapper.map(termService.updateTerm(request, id), TermListDTO.class);
        return buildItemResponse(response);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_TERM')")
    public ResponseEntity<?> deleteSelectedTerm(@PathVariable String id){
        termService.deleteTerm(id);
        String message = "Xóa thành công học kỳ "+id;
        return buildItemResponse(message);
    }
}
