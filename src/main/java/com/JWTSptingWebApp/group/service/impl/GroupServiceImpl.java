package com.JWTSptingWebApp.group.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.JWTSptingWebApp.UserDetail.Repository.UserDetailRepository;
import com.JWTSptingWebApp.UserDetail.model.UserDetail;
import com.JWTSptingWebApp.group.dto.GroupUserDto;
import com.JWTSptingWebApp.group.model.Group;
import com.JWTSptingWebApp.group.model.User;
import com.JWTSptingWebApp.group.repository.GroupRepository;
import com.JWTSptingWebApp.group.repository.UserRepositoryGrp;
import com.JWTSptingWebApp.group.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService {
	@Autowired
	UserRepositoryGrp userRepository;
	@Autowired
	GroupRepository groupRepository;
	@Autowired
	UserDetailRepository detailRepository;

	public GroupServiceImpl() {
	}

	// add
	public ResponseEntity<?> CreateorUpdate(GroupUserDto dto) {
		Map<String, Object> mapvalid = new HashMap<String, Object>();

//		String ss = 

		String[] st = dto.getGroupUsers();
		System.out.println(st);
		System.out.println(dto.getGroupUsers());
		if (st==null) {
			mapvalid.put("message", "User empty not allowed");
			mapvalid.put("data", dto.getGroupUsers());
			mapvalid.put("status", false);
			mapvalid.put("code", 404);
			return ResponseEntity.status(HttpStatus.OK).body(mapvalid);
		}
		if (groupRepository.getGroupName(dto.getGroupName()) != null) {

			mapvalid.put("message", "Group Already Exist");
			mapvalid.put("data", dto.getGroupName());
			mapvalid.put("status", false);
			mapvalid.put("code", 404);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapvalid);

		}
		for (String s1 : st) {
			if (detailRepository.findByUserToke(s1) != null && s1!=null) {
//			return ResponseEntity.ok(s1);
				System.out.println("added");
			} else {
				mapvalid.put("message", "User not found");
				mapvalid.put("data", s1);
				mapvalid.put("status", false);
				mapvalid.put("code", 404);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapvalid);
			}

		}
		dto.setGroupToken(generateRandom());

		return DtoToModelGroup(dto);

	}

	// findBytoken
	public ResponseEntity<?> findGroupByToken(String token) {
		Map<String, Object> map = new HashMap<String, Object>();

		Map<String, Object> mapall = new HashMap<String, Object>();
		try {
			Optional<Group> grp1 = groupRepository.findByToken(token);
			if (grp1 != null) {
				Group grp = grp1.get();
				String[] uer1 = userRepository.getUser(grp.getGid());

				map.put("groupName", grp.getGroupname());
				map.put("groupType", grp.getGrouptype());
				map.put("groupUsers", Arrays.stream(uer1));
				map.put("token", grp.getToken());

				mapall.put("code", 200);
				mapall.put("status", true);
				mapall.put("data", map);
				mapall.put("message", "Group is here");
				return ResponseEntity.ok(mapall);
			} else {
				map.put("code", 404);
				map.put("status", false);
//			map.put("data","");
				map.put("message", "Group Not Found");
				return ResponseEntity.ok(map);
			}
		} catch (java.util.NoSuchElementException e) {
			map.put("code", 404);
			map.put("status", false);
			map.put("message", "Group is Not Present");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		}
	}

	public ResponseEntity<?> DeleteByToken(String token) {

		Map<String, Object> map = new HashMap<String, Object>();

		if (token.length() > 8 || token.length() < 8) {
			map.put("code", 404);
			map.put("status", false);
			map.put("message", "Token Is Invaild,Token must be 8 character");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		} else {

			try {

				Optional<Group> grp1 = groupRepository.findByToken(token);

				Group grpall = grp1.get();
				map.put("code", 200);
				map.put("status", true);
				map.put("data", grpall.getToken());
				map.put("message", "Above Group deleted Succeffully");
				userRepository.deleteebygid(grpall.getGid());
				groupRepository.deleteebytoken(token);
				return ResponseEntity.ok(map);

			} catch (java.util.NoSuchElementException e) {
				map.put("code", 404);
				map.put("status", false);
				map.put("message", "Group Not Present");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
			}

		}
//		return ResponseEntity.ok("DEleted");
	}

	public ResponseEntity<?> UpdateByToken(String token, GroupUserDto dto) {
		int flag=0;

		Map<String, Object> map = new HashMap<String, Object>();

		if (token.length() > 8 || token.length() < 8) {
			map.put("code", 404);
			map.put("status", false);
			map.put("message", "Token IS Invaild,Token must be 8 character");
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		}
		if (dto.getGroupUsers() == null) {
			map.put("message", "User empty not allowed");
			map.put("data", dto.getGroupUsers());
			map.put("status", false);
			map.put("code", 404);
			return ResponseEntity.status(HttpStatus.OK).body(map);
		}
		if (groupRepository.getGroupNamebytoken(token).equals(dto.getGroupName())) {
			flag++;
			System.out.println("group mached");
			Map<String, Object> mapvalid = new HashMap<String, Object>();

//			String ss = dto.getGroupUsers();
			String[] st = dto.getGroupUsers();// ss.split(",");

			for (String s1 : st) {
				if (detailRepository.findByUserToke(s1) != null) {
//				return ResponseEntity.ok(s1);
					System.out.println("added");
				} else {
					mapvalid.put("message", "User not found");
					mapvalid.put("data", s1);
					mapvalid.put("status", false);
					mapvalid.put("code", 404);
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapvalid);
				}
			}

			try {

				Optional<Group> grp1 = groupRepository.findByToken(token);
				Group grpall = grp1.get();
				userRepository.deleteebygid(grpall.getGid());
				return DtoToModelGroupUpdate(dto, grpall, token);

			} catch (java.util.NoSuchElementException e) {
				map.put("code", 404);
				map.put("status", false);
				map.put("message", "Group is Not Present");
				ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
			}
			return ResponseEntity.ok(map);
		}
		
	else {
			if (flag== 0 && groupRepository.getGroupName(dto.getGroupName()) != null  ) {
				System.out.println(flag);
				map.put("message", "Group Already Exist");
				map.put("data", dto.getGroupName());
				map.put("status", false);
				map.put("code", 404);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);

			}
			System.out.println(flag);
			Map<String, Object> mapvalid = new HashMap<String, Object>();

//			String ss = dto.getGroupUsers();
			String[] st = dto.getGroupUsers();// ss.split(",");

			for (String s1 : st) {
				if (detailRepository.findByUserToke(s1) != null) {
//				return ResponseEntity.ok(s1);
					System.out.println("added");
				} else {
					mapvalid.put("message", "User not found");
					mapvalid.put("data", s1);
					mapvalid.put("status", false);
					mapvalid.put("code", 404);
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapvalid);
				}
			}

			try {

				Optional<Group> grp1 = groupRepository.findByToken(token);
				Group grpall = grp1.get();
				userRepository.deleteebygid(grpall.getGid());
				return DtoToModelGroupUpdate(dto, grpall, token);

			} catch (java.util.NoSuchElementException e) {
				map.put("code", 404);
				map.put("status", false);
				map.put("message", "Group is Not Present");
				ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
			}
		}
		return ResponseEntity.ok(map);
	}

	public ResponseEntity<?> Listalldata() {

		List<Map<String, Object>> list = null;
		Map<String, Object> mapadd = new HashMap<String, Object>();
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();

		List<Group> gr1 = groupRepository.findAll();
		if (gr1.isEmpty()) {
			mapadd.put("code", 404);
			mapadd.put("status", false);
			mapadd.put("message", "Craete new Group,No groups are found ");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapadd);

		}
		for (Group grp : gr1) {
			Map<String, Object> map = new HashMap<String, Object>();
			String[] uer1 = userRepository.getUser(grp.getGid());

			list = new ArrayList<Map<String, Object>>();
			map.put("groupName", grp.getGroupname());
			map.put("groupType", grp.getGrouptype());
			map.put("groupUsers", Arrays.stream(uer1));

			map.put("token", grp.getToken());

//			for (User urp : uer1) {
//				Map<String, Object> map1 = new HashMap<String, Object>();
//
//				map1.put("Username", urp.getUsername());
//				map1.put("Token", urp.getToken());
//				// System.out.println(urp.getUsername());
//				list.add(map1);
//
//			}
//			map.put("Users", list);

			list2.add(map);

		}
		mapadd.put("code", 200);
		mapadd.put("status", true);
		mapadd.put("data", list2);
		mapadd.put("message", "All Groups Detail");
		return ResponseEntity.ok(mapadd);
	}

	public ResponseEntity<?> userdetails() {
		Map<String, Object> mapvalid = new HashMap<String, Object>();
//		Map<Object, Object> map = new HashMap<Object, Object>();

//		String[] uer1 =detailRepository.getUsernames();
		String[] uer2 = detailRepository.getTokens();

		List<UserDetail> ud = detailRepository.findAll();
		List<Map<String, Object>> list2 = null;
		String array[] = null;
		int i = 0;
//		for(String user:uer2)
//		{
//			array=user.split(" ");
//			i++;
//		}
//		String result = uer.replaceAll("^\"+|\"+$", "");
//		int[] array = Arrays.stream(uer2).mapToInt(Integer::parseInt).toArray();
		list2 = new ArrayList<Map<String, Object>>();

		for (UserDetail ud1 : ud) {
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("label", ud1.getUsername());
			map.put("value", ud1.getTokenuser());
			list2.add(map);

		}
//		int[] a= {1,2,3};

//		map.put("tokens", String.join(",", uer2));
		mapvalid.put("data", list2);
		mapvalid.put("status", true);
		mapvalid.put("code", 200);
		mapvalid.put("message", "UsersDetail");

		return ResponseEntity.ok(mapvalid);
	}

	public ResponseEntity<?> DtoToModelGroup(GroupUserDto dto) {
		Map<String, Object> mapvalid = new HashMap<String, Object>();
		String grpname = dto.getGroupName();
		String grptype = dto.getGroupType();
		String[] users = dto.getGroupUsers();

		if (grpname.isEmpty()) {
			mapvalid.put("code", 404);
			mapvalid.put("status", false);
			mapvalid.put("message", "VAlue is Not vaild");
			mapvalid.put("groupName", grpname);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapvalid);
		}
		if (grptype.isEmpty()) {
			mapvalid.put("code", 404);
			mapvalid.put("status", false);
			mapvalid.put("message", "VAlue is Not vaild");
			mapvalid.put("groupType", grptype);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapvalid);
		}
		if (users.toString().isEmpty()) {
			mapvalid.put("code", 404);
			mapvalid.put("status", false);
			mapvalid.put("message", "VAlue is Not vaild");
			mapvalid.put("groupUsers", users);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapvalid);
		} else {

			Map<String, Object> map = new HashMap<String, Object>();

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		String grouptoken=generateRandom();
			Group gg = new Group();
			gg.setGroupname(dto.getGroupName());
			gg.setGrouptype(dto.getGroupType());
			gg.setToken(dto.getGroupToken());
			groupRepository.save(gg);

//		 map.put("Groupname", dto.getGroupname());
//		 map.put("Grouptype", dto.getGrouptype());
//		 map.put("GroupToken", grouptoken);

//	String ss = dto.getGroupUsers();
//	String[] uer1=ss;
			String[] st = dto.getGroupUsers();// ss.split(",");

			for (String s1 : st) {
				Map<String, Object> mapdemo = new HashMap<String, Object>();

				User uu = new User();
				String token = generateRandom();
//			mapdemo.put("user:", s1);
//			mapdemo.put("token:", token);
//			list.add( mapdemo);
				uu.setUsername(s1);
				uu.setToken(token);
				uu.setGroup(gg);
				userRepository.save(uu);

			}
//			map.put("User:", dto);

			map.put("groupName", dto.getGroupName());
			map.put("groupType", dto.getGroupType());
			map.put("groupUsers", Arrays.stream(st));
			map.put("token", dto.getGroupToken());

			mapvalid.put("data", map);
			mapvalid.put("status", true);
			mapvalid.put("code", 200);
			mapvalid.put("message", "Group created succesfully");

			return ResponseEntity.status(HttpStatus.OK).body(mapvalid);

		}

	}

	public ResponseEntity<?> DtoToModelGroupUpdate(GroupUserDto dto, Group gg, String alredytoken) {
		Map<String, Object> mapvalid = new HashMap<String, Object>();

		String grpname = dto.getGroupName();
		String grptype = dto.getGroupType();
		String[] users = dto.getGroupUsers();

		if (grpname.isEmpty()) {
			mapvalid.put("code", 404);
			mapvalid.put("status", false);
			mapvalid.put("message", "Value is Not vaild");
			mapvalid.put("GroupName", grpname);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapvalid);
		}
		if (grptype.isEmpty()) {
			mapvalid.put("code", 404);
			mapvalid.put("status", false);
			mapvalid.put("message", "Value is Not vaild");
			mapvalid.put("GroupType", grptype);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapvalid);
		}
		if (users.toString().isEmpty()) {
			mapvalid.put("code", 404);
			mapvalid.put("status", false);
			mapvalid.put("message", "Value is Not vaild");
			mapvalid.put("GroupUsers", users);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapvalid);
		} else {

			Map<String, Object> map = new HashMap<String, Object>();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			gg.setGroupname(dto.getGroupName());
			gg.setGrouptype(dto.getGroupType());
			groupRepository.save(gg);

//			map.put("groupname", dto.getGroupName());
//			map.put("grouptype", dto.getGroupType());
			map.put("token", alredytoken);
//			map.put("groupUsers", dto.getGroupUsers());

//			String ss = dto.getGroupUsers();
			String[] st = dto.getGroupUsers();// ss.split(",");

			for (String s1 : st) {
				Map<String, Object> mapdemo = new HashMap<String, Object>();
				User uu = new User();
				String token = generateRandom();
				mapdemo.put("user", s1);
				mapdemo.put("token", token);
				list.add(mapdemo);
				uu.setUsername(s1);
				uu.setToken(token);
				uu.setGroup(gg);

				userRepository.save(uu);
			}
//			map.put("User:", list);
			map.put("groupName", dto.getGroupName());
			map.put("groupType", dto.getGroupType());
			map.put("groupUsers", Arrays.stream(st));
//			map.put("token", dto.getGroupToken());

			mapvalid.put("data", map);
			mapvalid.put("status", true);
			mapvalid.put("code", 200);
			mapvalid.put("message", "Group Updated succesfully");

			return ResponseEntity.status(HttpStatus.OK).body(mapvalid);
		}
	}

//	public void DtoToModelUser(GroupUserDto dto, Group gg) {
//		Map<String, Object> map = new HashMap<String, Object>();
//
//		String ss = dto.getGroupUsers();
//		String[] st = ss.split(",");
//		for (String s1 : st) {
//			User uu = new User();
//			uu.setUsername(s1);
//			uu.setToken(generateRandom());
//			uu.setGroup(gg);
//
//			userRepository.save(uu);
//
//		}
//
//	}

	private static String generateRandom() {
		String aToZ = "01234ABCDEFGHIJKLMNO012345PQRSTUVWXYZ678901235abcdefghijklmnopqrstuvwxyz6789";
		Random rand = new Random();
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			int randIndex = rand.nextInt(aToZ.length());
			res.append(aToZ.charAt(randIndex));

		}

		return res.toString();
	}
}
