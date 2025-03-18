package org.anjanx44.userservice.util;

//import io.smallrye.jwt.build.Jwt;
//
//public class JwtUtil {
//    private static final String SECRET_KEY = "V4g6v6Y3f7s2tBxZy8S5Rrz9EdR5KQ0Qf7IcYH0g7Jk=";
//    private static final String ISSUER = "my-auth-server";
//    private static final String AUDIENCE = "my-api";
//
//    public static String generateToken(String username) {
//        return Jwt.issuer(ISSUER)
//                .audience(AUDIENCE)
//                .subject(username)
//                .expiresAt(System.currentTimeMillis() + 3600 * 1000) // Token expires in 1 hour
//                .signWithSecret(SECRET_KEY);
//    }
//}
