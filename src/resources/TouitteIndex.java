package resources;

import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class TouitteIndex
{
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	@PrimaryKey
	Key key;

	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	Set<String> receivers;

	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	Touitte touitte;

	public Set<String> getReceivers() {
		return receivers;
	}

	public void setReceivers(Set<String> receivers) {
		this.receivers = receivers;
	}

	public Touitte getTouitte() {
		return touitte;
	}

	public void setTouitte(Touitte touitte) {
		this.touitte = touitte;
	}


}
