package vn.edu.vnua.fita.student.service.iservice;

import org.springframework.data.domain.Page;
import vn.edu.vnua.fita.student.entity.table.Term;
import vn.edu.vnua.fita.student.request.admin.term.CreateTermRequest;
import vn.edu.vnua.fita.student.request.admin.term.GetTermListRequest;

public interface ITermService {
    Page<Term> getTermList(GetTermListRequest request);
    Term createTerm(CreateTermRequest request);
    Term deleteTerm(String id);
}
