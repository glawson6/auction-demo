package com.ttis.security;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class FileUserDetailsServiceImpl implements AuthenticationUserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUserDetailsServiceImpl.class);

    private String userDetailsJSONFile;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Override
    public UserDetails loadUserDetails(Authentication authentication) throws UsernameNotFoundException {

        String userName = (String)authentication.getPrincipal();
        LOGGER.debug("uid received is {}", userName);

        UserDetails principal = null;

        try {
            TTISUserToken userToken = null;
            Resource resource = resourceLoader.getResource(userDetailsJSONFile);
            String userDetailsJson = IOUtils.toString(new InputStreamReader(resource.getInputStream()));
            LOGGER.debug("Found file {}...",resource.getURI().toString());
            if(StringUtils.isNotEmpty(userDetailsJson)) {
                userToken = mapper.readValue(userDetailsJson, new TypeReference<TTISUserToken>(){});
            }
            principal = new TTISUser(userToken);

        } catch (JsonParseException ex ){
            LOGGER.error("Error while parsing the userdetails Json",  ex);
            throw new UsernameNotFoundException(userName + " does not exist.");
        } catch (JsonMappingException ex ){
            LOGGER.error("Error while mapping the userdetails Json", ex);
            throw new UsernameNotFoundException(userName + " does not exist.");
        } catch (UsernameNotFoundException ex ){
            LOGGER.error("Exception while loading userdetails for user {}", userName, ex);
            throw new UsernameNotFoundException(userName + " does not exist.");
        } catch (IOException ex ){
            LOGGER.error("Exception while loading userdetails josn {}", userName, ex);
            throw new UsernameNotFoundException(userName + " does not exist.");
        }

        if (principal == null ){
            LOGGER.error("user id  {} not found in local json", userName);
            throw new UsernameNotFoundException(userName + " does not exist.");
        }
        return principal;
    }

    public String getUserDetailsJSONFile() {
        return userDetailsJSONFile;
    }

    public void setUserDetailsJSONFile(String userDetailsJSONFile) {
        this.userDetailsJSONFile = userDetailsJSONFile;
    }
}
