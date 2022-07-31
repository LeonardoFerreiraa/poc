db.createUser(
    {
        user: "root",
        pwd: "toor",
        roles: [
            {
                role: "readWrite",
                db: "poc_simple_crud"
            }
        ]
    }
);
