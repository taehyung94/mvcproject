package mvc.model;

import org.apache.commons.codec.digest.DigestUtils;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberDTO {
	private long id;
	private String loginId;
	private String loginPassword;
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = DigestUtils.sha512Hex(loginPassword);
	}
}
