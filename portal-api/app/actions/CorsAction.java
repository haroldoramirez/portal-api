package actions;

import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;

public class CorsAction extends Action.Simple {

    public CorsAction(Action<?> action) {
        this.delegate = action;
    }

    @Override
	public Promise<Result> call(Context context) throws Throwable {

        //Promise<Result> result = this.delegate.call(context);
        Http.Response response = context.response();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, " +
                "Content-Type, Accept, Authorization, X-Auth-Token");
        return delegate.call(context);

/*

	    //Handle preflight requests
	    if(context.request().method().equals("OPTIONS")) {
	    	response.setHeader("Access-Control-Allow-Origin", "*");
	        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
	        response.setHeader("Access-Control-Max-Age", "3600");
	        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, " +
                                 "Content-Type, Accept, Authorization, X-Auth-Token");
	        response.setHeader("Access-Control-Allow-Credentials", "true");
	        return result;
	    }

	   response.setHeader("Access-Control-Allow-Headers","X-Requested-With, Content-Type, X-Auth-Token");
       return result;*/
	}

}