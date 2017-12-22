package api;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;

import resources.Touitte;
import resources.TouitteIndex;
import resources.User;

@Api(
		name="petittouitte",
		version="v1",
		description="The PetitTouitte API"
		)
public class PetitTouitteAPI
{
	@SuppressWarnings("unchecked")
	@ApiMethod(name = "user.get.fromid", path = "user_fromid")
	public User getUserFromId(@Named("id") Long id)
	{
		PersistenceManager pm = getPersistenceManager();

		Query query = pm.newQuery(User.class);

		query.setFilter("id == " + id);

		List<User> result = (List<User>) pm.newQuery(query).execute();

		if (result.size() == 0)
			return null;

		User user = result.get(0);

		return user;
	}

	@ApiMethod(name = "user.follow")
	public User followUser(@Named("idFollower") Long idFollower, @Named("idFollowing") Long idFollowing) throws Exception
	{
		PersistenceManager pm = getPersistenceManager();
		
		Transaction tx = pm.currentTransaction();	
		
		User follower = null;
		
		try
		{
			tx.begin();

			follower = pm.getObjectById(User.class, idFollower);
			User following = pm.getObjectById(User.class, idFollowing);

			follower.getFollowing().add(idFollowing);
			following.getFollowers().add(idFollower);

			follower.setFollowingCount(follower.getFollowingCount() + 1);
			following.setFollowersCount(following.getFollowersCount() + 1);
			
			tx.commit();
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
		}
		
		pm.close();
		
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
		
		if (user.getFollowing() == null)
			user.setFollowing(new HashSet<Long>());

		if (user.getFollowers() == null)
			user.setFollowers(new HashSet<Long>());
		
		PersistenceManager pm = getPersistenceManager();

		pm.makePersistent(user);
		
		user.getFollowers().add(user.getId());

		pm.close();

		return user;
	}

	@ApiMethod(name ="user.touittes")
	@SuppressWarnings("unchecked")
	public List<Touitte> userTouitte(@Named("iduser") Long id)
	{
		PersistenceManager pm = getPersistenceManager();

		Query query = pm.newQuery(Touitte.class);

		query.setFilter("author == " + id);

		return (List<Touitte>) pm.newQuery(query).execute();
	}

	@ApiMethod(name = "user.timeline")
	@SuppressWarnings("unchecked")
	public List<Touitte> userTimeline(@Named("iduser") Long id)
	{
		PersistenceManager pm = getPersistenceManager();

		Query query = pm.newQuery(TouitteIndex.class);

		query.setFilter("receivers == " + id);

		List<TouitteIndex> indexes = (List<TouitteIndex>) pm.newQuery(query).execute();

		LinkedList<Touitte> list = new LinkedList<Touitte>();

		for (TouitteIndex index : indexes)
			list.add(index.getTouitte());

		return list;
	}
	
	@SuppressWarnings("unchecked")
	@ApiMethod(name = "user.get.fromalias", path = "user_fromalias")
	public User getUserFromAlias(@Named("alias") String alias)
	{
		PersistenceManager pm = getPersistenceManager();

		Query query = pm.newQuery(User.class);

		query.setFilter("alias == \"" + alias + "\"");
		 		
		List<User> result = (List<User>) pm.newQuery(query).execute();

		if (result.size() == 0)
			return null;

		User user = result.get(0);

		return user;
	}


	@ApiMethod(name = "touitte.post")
	public Touitte postTouitte(@Named("idAuthor") Long idUserAuthor, @Named("content") String content) throws Exception
	{
		//Author
		User author = getUserFromId(idUserAuthor);

		if (author == null)
			throw new Exception("Invalid author");

		//Touitte
		Touitte touitte = new Touitte();

		touitte.setAuthor(idUserAuthor);
		touitte.setDate(System.currentTimeMillis());
		touitte.setContent(content);

		//Index
		TouitteIndex index = new TouitteIndex();

		index.setTouitte(touitte);
		index.setReceivers(author.getFollowers());

		PersistenceManager pm = getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try
		{
			tx.begin();
			pm.makePersistent(index);
			tx.commit();
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
		}

		pm.close();

		return touitte;
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
