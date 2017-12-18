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
	//TODO Add request time

	@SuppressWarnings("unchecked")
	@ApiMethod(name = "user.get")
	public User getUser(@Named("id") Long id)
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

	@ApiMethod(name = "touitte.post")
	public Touitte postTouitte(@Named("idAuthor") Long idUserAuthor, @Named("content") String content) throws Exception
	{
		//Author
		User author = getUser(idUserAuthor);

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
		index.setReceivers(new HashSet<Long>(author.getFollowers()));

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
