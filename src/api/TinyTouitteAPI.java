package api;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;

import resources.User;

@Api(
  name="tinytouitte",
  version="v1",
  description="The TinyTouitte API"
)
public class TinyTouitteAPI {
	@SuppressWarnings("unchecked")
	@ApiMethod(name = "user.get")
	public User get(@Named("id") Long id) throws Exception
	{
		PersistenceManager pm = getPersistenceManager();

    Query query = pm.newQuery(User.class);

    query.setFilter("id == " + id);

    List<User> result = (List<User>) pm.newQuery(query).execute();

    if (result.size() == 0)
      throw new Exception("User not found");

    return result.get(0);
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
