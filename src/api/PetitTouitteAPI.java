package api;

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
		
		//TODO Add followers and following

		return result.get(0);
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
