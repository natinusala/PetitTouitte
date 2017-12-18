package resources;

import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class TouitteIndex
{
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	@PrimaryKey
	Long id;

	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	Set<Long> receivers;

	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	Touitte touitte;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Long> getReceivers() {
		return receivers;
	}

	public void setReceivers(Set<Long> receivers) {
		this.receivers = receivers;
	}

	public Touitte getTouitte() {
		return touitte;
	}

	public void setTouitte(Touitte touitte) {
		this.touitte = touitte;
	}


}
