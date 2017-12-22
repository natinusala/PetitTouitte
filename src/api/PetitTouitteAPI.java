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
	@ApiMethod(name = "user.follow")
	public User followUser(@Named("aliasFollower") String aliasFollower, @Named("aliasFollowing") String aliasFollowing) throws Exception
	{
		PersistenceManager pm = getPersistenceManager();

		Transaction tx = pm.currentTransaction();

		User follower = null;

		try
		{
			tx.begin();

			follower = pm.getObjectById(User.class, aliasFollower);
			User following = pm.getObjectById(User.class, aliasFollowing);

			follower.getFollowing().add(following.getAlias());
			following.getFollowers().add(follower.getAlias());

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
	
	public static class BenchmarkResult
	{
		public boolean success;
		public long timeInMillis;
	}
	
	@ApiMethod(name = "benchmark.purge")
	public BenchmarkResult purge()
	{
		BenchmarkResult result = new BenchmarkResult();
		long time = System.currentTimeMillis();
		
		PersistenceManager pm = getPersistenceManager();
		
		Query query = pm.newQuery(User.class);
		query.deletePersistentAll();
		
		query = pm.newQuery(TouitteIndex.class);
		query.deletePersistentAll();
		
		query = pm.newQuery(Touitte.class);
		query.deletePersistentAll();
		
		pm.close();
		
		result.timeInMillis = System.currentTimeMillis() - time;
		result.success = true;
		
		return result;
	}
	
	@ApiMethod(name = "benchmark.createusers")
	public BenchmarkResult createUsers(@Named("count") long count) throws Exception
	{
		BenchmarkResult result = new BenchmarkResult();
		long time = System.currentTimeMillis();
		
		for (int i = 0; i < count; i++)
		{
			User user = new User();
			user.setAlias("n" + i);
			user.setName("User" + i);
			insertUser(user);
		}
		
		result.timeInMillis = System.currentTimeMillis() - time;
		result.success = true;
		
		return result;
	}
	
	@ApiMethod(name = "benchmark.followfirstuser")
	public BenchmarkResult followFirstUser(@Named("max") long max) throws Exception
	{
		BenchmarkResult result = new BenchmarkResult();
		long time = System.currentTimeMillis();
		
		for (int i = 1; i < max; i++)
		{
			followUser("n" + i, "n0");
		}
		
		result.timeInMillis = System.currentTimeMillis() - time;
		result.success = true;
		
		return result;
	}
	
	@ApiMethod(name = "benchmark.touitte")
	public BenchmarkResult benchmarkTouitte() throws Exception
	{
		BenchmarkResult result = new BenchmarkResult();
		long time = System.currentTimeMillis();
		
		postTouitte("n0", "Touitte de banc de test : " + System.currentTimeMillis());
		
		result.timeInMillis = System.currentTimeMillis() - time;
		result.success = true;
		
		return result;
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
			user.setFollowing(new HashSet<String>());

		if (user.getFollowers() == null)
			user.setFollowers(new HashSet<String>());

		PersistenceManager pm = getPersistenceManager();

		pm.makePersistent(user);

		user.getFollowers().add(user.getAlias());

		pm.close();

		return user;
	}

	@ApiMethod(name ="user.touittes")
	@SuppressWarnings("unchecked")
	public List<Touitte> userTouitte(@Named("aliasUser") String alias)
	{
		PersistenceManager pm = getPersistenceManager();

		Query query = pm.newQuery(Touitte.class);

		query.setFilter("author == \"" + alias + "\"");

		return (List<Touitte>) pm.newQuery(query).execute();
	}

	@ApiMethod(name = "user.timeline")
	@SuppressWarnings("unchecked")
	public List<Touitte> userTimeline(@Named("aliasUser") String alias)
	{
		PersistenceManager pm = getPersistenceManager();

		Query query = pm.newQuery(TouitteIndex.class);

		query.setFilter("receivers == \"" + alias + "\"");

		List<TouitteIndex> indexes = (List<TouitteIndex>) pm.newQuery(query).execute();

		LinkedList<Touitte> list = new LinkedList<Touitte>();

		for (TouitteIndex index : indexes)
			list.add(index.getTouitte());

		return list;
	}

	@SuppressWarnings("unchecked")
	@ApiMethod(name = "user.get")
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
	public Touitte postTouitte(@Named("aliasAuthor") String aliasUserAuthor, @Named("content") String content) throws Exception
	{
		//Author
		PersistenceManager pm = getPersistenceManager();

		User author = pm.getObjectById(User.class, aliasUserAuthor);

		if (author == null)
			throw new Exception("Invalid author");

		//Touitte
		Touitte touitte = new Touitte();

		touitte.setAuthor(aliasUserAuthor);
		touitte.setDate(System.currentTimeMillis());
		touitte.setContent(content);

		//Index
		TouitteIndex index = new TouitteIndex();

		index.setTouitte(touitte);
		index.setReceivers(author.getFollowers());

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
}
