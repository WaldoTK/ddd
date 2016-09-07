package AA102g5_2.member.model;
import java.util.*;



public interface MemberDAO_interface {
	int insert(MemberVO memberVO);
	int update(MemberVO memberVO, byte[] image);
	public void delete(Integer id);
	public MemberVO findByPrimaryKey(Integer id);
	public List<MemberVO> getAll();
	byte[] getImage(int id);
    public MemberVO login(String account);
	
	
	
}
