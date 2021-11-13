package com.example.gymmanagement.request;

import com.example.gymmanagement.Parametros;
import com.example.gymmanagement.api.ActivityApi;
import com.example.gymmanagement.api.PaymentApi;
import com.example.gymmanagement.api.PlanApi;
import com.example.gymmanagement.api.UserApi;
import com.example.gymmanagement.model.*;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;

public class Request {

    public Call<ArrayList<PaymentResponse>> getAllPayments(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Parametros.HOST + "payment/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();


        PaymentApi paymentApi = retrofit.create(PaymentApi.class);
        Call<ArrayList<PaymentResponse>> call = paymentApi.getAllPayments();

        return call;
    }

    public Call<PaymentReportResponse> getPaymentReport(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Parametros.HOST + "payment/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();


        PaymentApi paymentApi = retrofit.create(PaymentApi.class);
        Call<PaymentReportResponse> call = paymentApi.getPaymentReport();

        return call;
    }

    public Call<ArrayList<Activity>> getAllActivities(Integer planId){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Parametros.HOST + "plan/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();


        PlanApi planApi = retrofit.create(PlanApi.class);
        Call<ArrayList<Activity>> call = planApi.getActivitiesByPlan(planId);

        return call;
    }

    public Call<ArrayList<UserResponse>> getAllUsers(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Parametros.HOST + "user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();


        UserApi userApi = retrofit.create(UserApi.class);
        Call<ArrayList<UserResponse>> call = userApi.getAllUsers();

        return call;
    }

    public Call<ArrayList<Plan>> getPlans(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Parametros.HOST + "plan/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();


        PlanApi planApi = retrofit.create(PlanApi.class);
        Call<ArrayList<Plan>> call = planApi.getPlans();

        return call;
    }

    public Call<ArrayList<UserResponse>> getAllUsersDisabled(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.31.151:8089/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();


        UserApi userApi = retrofit.create(UserApi.class);
        Call<ArrayList<UserResponse>> call = userApi.getAllUsersDisabled();

        return call;
    }


    public Call<UserResponse> getUserById(Integer userId){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Parametros.HOST + "user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();


        UserApi userApi = retrofit.create(UserApi.class);
        Call<UserResponse> call = userApi.getUserById(userId);

        return call;
    }

    public Call<User> getId(String email, String password){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Parametros.HOST + "user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        final UserApi userApi = retrofit.create(UserApi.class);

        Call<User> call = userApi.getId(email, password);

        return call;
    }

    public Call<UserActivity> activityRegister(Integer userId, Integer activityId){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl(Parametros.HOST + "activity/")
                //.baseUrl("http://192.168.31.148:8081/v1/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        ActivityApi activityApi= retrofit.create(ActivityApi.class);


        UserActivity user = new UserActivity();
        user.setUserId(userId);
        user.setActivityId(activityId);

        Call<UserActivity> call = activityApi.activityRegister(user);

        return call;
    }


    public Call<User> createUser(Integer ci, Integer userType, String userName, String lastName, String email, String password, Integer regitrantId, Integer planId){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl(Parametros.HOST + "user/")
                //.baseUrl("http://192.168.31.148:8081/v1/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        UserApi userApi= retrofit.create(UserApi.class);


        User user = new User();
        user.setIdUser(ci);
        user.setUserTypeId(userType);
        user.setUserName(userName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRegistrantId(regitrantId);
        user.setPlanId(planId);

        Call<User> call = userApi.createUser(user);

        return call;
    }


    public Call<User> updateUser(Integer ci, Integer userType, String userName, String lastName, String email, String password, Integer regitrantId, Integer planId){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl(Parametros.HOST + "user/")
                //.baseUrl("http://192.168.31.148:8081/v1/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        UserApi userApi= retrofit.create(UserApi.class);


        User user = new User();
        user.setIdUser(ci);
        user.setUserTypeId(userType);
        user.setUserName(userName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRegistrantId(regitrantId);
        user.setPlanId(planId);

        Call<User> call = userApi.updateUser(user);

        return call;
    }

    public Call<User> updateUserPlan(User user){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl(Parametros.HOST + "user/")
                //.baseUrl("http://192.168.31.148:8081/v1/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        UserApi userApi= retrofit.create(UserApi.class);


//        User newUser = new User();
//        newUser.setUserTypeId(user.getUserTypeId());
//        newUser.setUserName(user.getUserName());
//        newUser.setLastName(user.getLastName());
//        newUser.setEmail(user.getEmail());
//        newUser.setPassword(user.getPassword());
//        newUser.setRegistrantId(user.getRegistrantId());
        user.setPlanId(user.getPlanId());

        Call<User> call = userApi.updateUser(user);

        return call;
    }


    public Call<User> enableUser(User user){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Parametros.HOST + "user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        final UserApi userApi= retrofit.create(UserApi.class);

        Call<User> call = userApi.enableUser(user);

        return call;
    }

    public Call<User> disableUser(User user){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Parametros.HOST + "user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        final UserApi userApi= retrofit.create(UserApi.class);

        Call<User> call = userApi.disableUser(user);

        return call;
    }

    public Call<Integer> updateCancelar(Integer userId, Integer activityId){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Parametros.HOST + "activity/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        final ActivityApi activityApi= retrofit.create(ActivityApi.class);

        UserActivity userActivity = new UserActivity();
        userActivity.setActivityId(activityId);
        userActivity.setUserId(userId);

        Call<Integer> call = activityApi.updateCancelar(userActivity);

        return call;

    }

    public Call<Integer> getUserType(Integer userId){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Parametros.HOST + "user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        final UserApi userApi= retrofit.create(UserApi.class);

        Call<Integer> call = userApi.getUserType(userId);

        return call;
    }

    public Call<ActivityResponse> activityScheduleById(Integer activityId){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Parametros.HOST + "activity/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        final ActivityApi activityApi= retrofit.create(ActivityApi.class);

        Call<ActivityResponse> call = activityApi.activityScheduleById(activityId);

        return call;
    }

    public Call<ArrayList<ActivityResponse>> getActivitiesByUserId(Integer userId){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Parametros.HOST + "activity/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        final ActivityApi activityApi= retrofit.create(ActivityApi.class);

        Call<ArrayList<ActivityResponse>> call = activityApi.activitiesByUserId(userId);

        return call;
    }


    public Call<ArrayList<ActivityResponse>> getActivityDetails(Integer activityId){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Parametros.HOST + "activity/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        final ActivityApi activityApi= retrofit.create(ActivityApi.class);

        Call<ArrayList<ActivityResponse>> call = activityApi.getActivityDetails(activityId);

        return call;
    }

    public Call<Integer> getPaymentByUserId(Integer userId){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Parametros.HOST + "payment/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        final PaymentApi paymentApi= retrofit.create(PaymentApi.class);

        Call<Integer> call = paymentApi.getPaymentByUserId(userId);

        return call;
    }

    public Call<PaymentDetails> getPaymentByUserIdDetails(Integer userId){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Parametros.HOST + "payment/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        final PaymentApi paymentApi= retrofit.create(PaymentApi.class);

        Call<PaymentDetails> call = paymentApi.getPaymentByUserIdDetails(userId);

        return call;
    }

    public Call<Payment> updatePayment(Integer userId, Integer status, Integer paymentId){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl(Parametros.HOST + "payment/")
                //.baseUrl("http://192.168.31.148:8081/v1/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        PaymentApi paymentApi= retrofit.create(PaymentApi.class);


        Payment payment = new Payment();
        payment.setPaymentId(paymentId);
        payment.setUserId(userId);
        if(status==0){
            payment.setStatus(1);
        }else{
            payment.setStatus(0);
        }

        Call<Payment> call = paymentApi.updatePayment(payment);

        return call;
    }


    public Call<Payment> addPayment(Integer userId){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl(Parametros.HOST + "payment/")
                //.baseUrl("http://192.168.31.148:8081/v1/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        PaymentApi paymentApi= retrofit.create(PaymentApi.class);

        Payment payment = new Payment();
        payment.setUserId(userId);

        Call<Payment> call = paymentApi.addPayment(payment);

        return call;
    }

    public Call<Payment> addPaymentCard(Integer userId){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl(Parametros.HOST + "payment/")
                //.baseUrl("http://192.168.31.148:8081/v1/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        PaymentApi paymentApi= retrofit.create(PaymentApi.class);

        Payment payment = new Payment();
        payment.setUserId(userId);

        Call<Payment> call = paymentApi.addPaymentCard(payment);

        return call;
    }

    public Call<String> getUserPlan(Integer userId){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Parametros.HOST + "user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();


        UserApi userApi = retrofit.create(UserApi.class);
        Call<String> call = userApi.getUserPlan(userId);

        return call;
    }

}
