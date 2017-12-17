package resources;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Transient;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class User
{
  @Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
  @PrimaryKey
  Long id;

  @Persistent
  String alias; //"@something"

  @Persistent
  String name; //"Firstname Lastname"

  @Transient
  private Long followers;
  
  @Transient
  private Long following;

  public Long getId() {
  	return id;
  }

  public void setId(Long id) {
  	this.id = id;
  }

  public String getAlias() {
  	return alias;
  }

  public void setAlias(String alias) {
  	this.alias = alias;
  }

  public String getName() {
  	return name;
  }

  public void setName(String name) {
  	this.name = name;
  }

  public Long getFollowers() {
  	return followers;
  }

  public void setFollowers(Long followers) {
  	this.followers = followers;
  }

  public Long getFollowing() {
  	return following;
  }

  public void setFollowing(Long following) {
  	this.following = following;
  }

}

