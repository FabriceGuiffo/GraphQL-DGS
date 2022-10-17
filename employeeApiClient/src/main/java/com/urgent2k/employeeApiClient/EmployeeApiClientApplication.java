package com.urgent2k.employeeApiClient;

import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.netflix.graphql.dgs.client.MonoGraphQLClient;
import com.netflix.graphql.dgs.client.WebClientGraphQLClient;
import com.urgent2k.employeeApiClient.Model.Company;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class EmployeeApiClientApplication implements CommandLineRunner {



	public static void main(String[] args) {
		SpringApplication.run(EmployeeApiClientApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		WebClient webClient=WebClient.create("http://localhost:9005/graphql");
		WebClientGraphQLClient client= MonoGraphQLClient.createWithWebClient(webClient);
		Map<String,Object> variables=new HashMap<>();
		variables.put("id",3);

		//first attempt at writing a xtured query string
		/*String query="{findCompany(id:1){\n"+
						"name\n"+
						"ceoname\n"+
						"location}\n"+
					"}";*/
		//first attempt at writing a xtured query string with parameterized inputs
		String query="query FindCompany($id:Int!){\n"+
				"findCompany(id:$id){\n"+
				"id\n"+
				"name\n"+
				"ceoname\n"+
				"contact\n"+
				"location}\n"+
				"}";

		//query string with parameterized input lacking one field to see if the created company class objected os created
		//the object is well instanciated with a null set at the missing property
		/*String query="query FindCompany($id:Int!){\n"+
				"findCompany(id:$id){\n"+
				"id\n"+
				"name\n"+
				"contact\n"+
				"location}\n"+
				"}";*/

		//a query string to extract a list of string representing graphql response
		/*String querylist="query AllEnterprises{\n"+
					"findAllCompanies{\n"+
					"id\n"+
					"name\n"+
					"contact}\n"+
					"}";*/

		// a mutation request exemple to create a new company we will retrieve an instance
		//we start by creating a variable set to parameterize input set
		/*Map<String,Object> mutationvariables=new HashMap<>();
		mutationvariables.put("name","Ntong");
		mutationvariables.put("ceoname","Guiffo");
		mutationvariables.put("contact",691416154);
		mutationvariables.put("location","cameroon");

		String mutationrequest="mutation CreateCompany($name:String!,$ceoname:String,$contact:Int,$location:String!){\n"+
				"createCompany(name:$name,ceoname:$ceoname,contact:$contact,location:$location){\n"+
				"id\n"+
				"name\n"+
				"ceoname\n"+
				"contact\n"+
				"location}\n"+
				"}";*/






		try{
			Mono<GraphQLResponse> graphQLResponseMono=client.reactiveExecuteQuery(query,variables);//applies for 1st 2 cases
			//extract mono with just ceoname example to try out extractValue method with a blocking mono content extraction
			//we left out the subscribe which is non blocking
			/*Mono<String> companyboss=graphQLResponseMono.map(r->r.extractValue("findCompany.ceoname"));
			String s=companyboss.block();
			System.out.println(s);*/

			//extract mono and deserialise to an object instance with extractValueAsObject using a subcribe non blocking mono content extraction
			Mono<Company> foundcompanymono=graphQLResponseMono.map(r->r.extractValueAsObject("findCompany",Company.class));
			foundcompanymono.subscribe(r->System.out.println(r));


			//ran a query to extract a list of all companies in json. In effect what we get is a list, reason why we do
			//findAllCompanies[index] to get list element with particular index or findAllCompanies[*] to get all elems
			//in jsonpath format, in the [*] case what we actually extract is a Map<String,String> object which we can manipulate accordingly
			//NB subscribe receives only Consumer fxn instances: a consumer being a fxnal interface with void return type
			//here we will use blocking because we can extract the actual list
			/*Mono<GraphQLResponse> graphQLResponseMono=client.reactiveExecuteQuery(querylist);
			Mono<List<Map<String,String>>> allcompaniesmono=graphQLResponseMono.map(r->r.extractValue("findAllCompanies[*]"));
			//allcompaniesmono.subscribe();//we leave this just for representation sake but this generates double queries if run with block
			List<Map<String,String>> setOfCompanies=allcompaniesmono.block();
			setOfCompanies.forEach(System.out::println);*/

			//ran a query to extract list of all companies and automatically deserialise to a list of company instances
			//using new TypeRef<List<Company>>(){}
			//this approach creates our instance elems using only the available data from the requests
			/*Mono<GraphQLResponse> graphQLResponseMono=client.reactiveExecuteQuery(querylist);
			Mono<List<Company>> allcompaniesmono=graphQLResponseMono.map(r->r.extractValueAsObject("findAllCompanies[*]",new TypeRef<List<Company>>(){}));
			List<Company> setOfCompanies=allcompaniesmono.block();
			setOfCompanies.forEach(System.out::println);*/

			//mutation request to create an element in database
			//with a mutation if we are using an operation name in order to parameterize in the input values,
			//make sure the input signature matches perfectly that of the schema for the corresponding ressolver
			//if not you get an error of PathNotFoundException from the server
			/*Mono<GraphQLResponse> graphQLResponseMono=client.reactiveExecuteQuery(mutationrequest,mutationvariables);
			Mono<Company> companymono=graphQLResponseMono.map(r->r.extractValueAsObject("createCompany",Company.class));
			Company createdCompany=companymono.block();
			System.out.println(createdCompany);*/




		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
