package com.marscolony.user.query.api.handlers;

import com.marscolony.user.query.api.dto.UserLookupResponse;
import com.marscolony.user.query.api.queries.FindAllUsersQuery;
import com.marscolony.user.query.api.queries.FindUserByIdQuery;
import com.marscolony.user.query.api.queries.SearchUsersQuery;

public interface UserQueryHandler {

    UserLookupResponse getUserById(FindUserByIdQuery findUserByIdQuery);
    UserLookupResponse searchUsers(SearchUsersQuery searchUsersQuery);
    UserLookupResponse getAllUsers(FindAllUsersQuery findAllUsersQuery);

}
