package AA102.g5.Case.model;

import java.util.*;

import AA102g5_2.member.model.MemberVO;

public interface CaseDAO_interface {
	      int insert(CaseVO caseVO);
          public void update(CaseVO caseVo);
          public void delete(Integer case_no);
          public CaseVO findByPrimaryKey(Integer case_no);
          public List<CaseVO> getAll();
          public List<CaseVO> getSome();
          public List<CaseVO> getWhere(String condition);
}
