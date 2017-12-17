package api;

import java.util.HashSet;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;

import resources.User;

@Api(
		name="petittouitte",
		version="v1",
		description="The PetitTouitte API"
		)
public class PetitTouitteAPI 
{
	//TODO Add request time 
	
	@SuppressWarnings("unchecked")
	@ApiMethod(name = "user.get")
	public User getUser(@Named("id") Long id) throws Exception
	{
		PersistenceManager pm = getPersistenceManager();

		Query query = pm.newQuery(User.class);

		query.setFilter("id == " + id);

		List<User> result = (List<User>) pm.newQuery(query).execute();

		if (result.size() == 0)
			throw new Exception("User not found");
		
		User user = result.get(0);
		
		return user;
	}
	
	@ApiMethod(name = "user.follow")
	public User followUser(@Named("idFollower") Long idFollower, @Named("idFollowing") Long idFollowing) throws Exception
	{
		User follower = getUser(idFollower);
		User following = getUser(idFollowing);
		
		if (follower.getFollowing() == null)
			follower.setFollowing(new HashSet<Long>());
		
		if (follower.getFollowers() == null)
			follower.setFollowers(new HashSet<Long>());
		
		if (following.getFollowers() == null)
			following.setFollowers(new HashSet<Long>());
		
		if (following.getFollowing() == null)
			following.setFollowing(new HashSet<Long>());
		
		follower.getFollowing().add(idFollowing);
		following.getFollowers().add(idFollower);
		
		follower.setFollowersCount(follower.getFollowers().size());
		follower.setFollowingCount(follower.getFollowing().size());
		
		following.setFollowersCount(following.getFollowers().size());
		following.setFollowingCount(following.getFollowing().size());
		
		return follower;
	}
	
	@SuppressWarnings("unchecked")
	@ApiMethod(name = "user.list")
	public List<User> listUsers()
	{
		PersistenceManager pm = getPersistenceManager();

		Query query = pm.newQuery(User.class);
		
		return (List<User>) pm.newQuery(query).execute();
	}

	@ApiMethod(name = "user.insert")
	public User insertUser(User user) throws Exception
	{
		if (user == null || user.getAlias() == null || user.getName() == null)
			throw new Exception("Invalid user");
		
		PersistenceManager pm = getPersistenceManager();

		pm.makePersistent(user);
		
		pm.close();
		
		return user;
	}

	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}

	/*
  @ApiMethod(name = "user.timeline")
	public TouitteCollection timeline(@Named("id") Long id, @Named("count") Long count, @Named("offset") Long offset)
	{
		PersistenceManager pm = getPersistenceManager();
    Query query = pm.newQuery();

	}
	 */
}
