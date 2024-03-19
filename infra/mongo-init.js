db.createUser(
    {
        user: "local_user",
        pwd: "local_password",
        roles: [
            {
                role: "userAdminAnyDatabase",
                db: "products-catalog"
            }
        ]
    }
);