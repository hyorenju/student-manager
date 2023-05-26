package vn.edu.vnua.qlsvfita.service.admin;

import org.springframework.data.domain.Page;
import vn.edu.vnua.qlsvfita.model.dto.TermListDTO;
import vn.edu.vnua.qlsvfita.model.entity.Term;
import vn.edu.vnua.qlsvfita.request.admin.term.CreateTermRequest;
import vn.edu.vnua.qlsvfita.request.admin.term.GetTermListRequest;
import vn.edu.vnua.qlsvfita.request.admin.term.UpdateTermRequest;

import java.util.List;

public interface TermService {
    Page<Term> getTermList(GetTermListRequest request);

    Term createTerm(CreateTermRequest request);

    Term updateTerm(UpdateTermRequest request, String termId);

    void deleteTerm(String id);
}
