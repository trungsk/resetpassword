package com.thanhtrung.loginsecurity.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thanhtrung.loginsecurity.entity.RegistrationUserToken;
import com.thanhtrung.loginsecurity.entity.User;
import com.thanhtrung.loginsecurity.entity.UserStatus;
import com.thanhtrung.loginsecurity.event.OnSendRegistrationUserConfirmViaEmailEvent;
import com.thanhtrung.loginsecurity.repository.RegistrationUserTokenRepository;
import com.thanhtrung.loginsecurity.repository.UserRepository;

@Service
@Component
@Transactional
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RegistrationUserTokenRepository registrationUserTokenRepository;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void createUser(User user) {

		// encode password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		// create user
		userRepository.save(user);

		// create new user registration token
		createNewRegistrationUserToken(user);

		// send email to confirm
		sendConfirmUserRegistrationViaEmail(user.getEmail());
	}

	private void createNewRegistrationUserToken(User user) {

		// create new token for confirm Registration
		final String newToken = UUID.randomUUID().toString();
		RegistrationUserToken token = new RegistrationUserToken(newToken, user);

		registrationUserTokenRepository.save(token);
	}

	private void sendConfirmUserRegistrationViaEmail(String email) {
		eventPublisher.publishEvent(new OnSendRegistrationUserConfirmViaEmailEvent(email));
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}


	@Override
	public void activeUser(String token) {
		RegistrationUserToken registrationUserToken = registrationUserTokenRepository.findByToken(token);

		User user = registrationUserToken.getUser();
		user.setStatus(UserStatus.ACTIVE);

		userRepository.save(user);

		// remove Registration User Token
		registrationUserTokenRepository.deleteById(registrationUserToken.getId());
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				AuthorityUtils.createAuthorityList(user.getRole()));
	}
}
