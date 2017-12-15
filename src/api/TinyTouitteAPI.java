package api;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;

import resources.User;

@Api(
  name="tinytouitte",
  version="v1"
)
public class TinyTouitteAPI {
	@ApiMethod(name = "user.get")
	public User get(@Named("id") Long id)
	{
		PersistenceManager pm = getPersistenceManager();

    Query query = pm.newQuery(User.class);

    query.setFilter("id == " + id);

    return (User) pm.newQuery(query).execute();
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
