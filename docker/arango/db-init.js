function parse_inputs(inputs) {
    if (!inputs) {
        throw "Input parameters are not defined";
    }

    let user_name = '';
    let password = '';
    let db_name = '';

    let isUserName = false;
    let isPassword = false;
    let isDbName = false;

    for (let i in inputs) {
        if (inputs[i] === "/u") {
            isUserName = true;
            isPassword = false;
            isDbName = false;
        } else if (inputs[i] === "/p") {
            isUserName = false;
            isPassword = true;
            isDbName = false;
        } else if (inputs[i] === "/d") {
            isUserName = false;
            isPassword = false;
            isDbName = true;
        } else if (isUserName) {
            user_name = inputs[i]
        } else if (isPassword) {
            password = inputs[i]
        } else if (isDbName) {
            db_name = inputs[i]
        }
    }

    return {
        user: user_name,
        password: password,
        dbName: db_name
    };
}

function create_user(users, name, password) {
    if (!users.exists(user)) {
        print('creating user ' + user + ' ...');
        users.save(user, password);
        print('user ' + user + ' has been created.');
    }
}

function create_grant_db(users, user, dbName) {
    if (!db._databases().includes(dbName)) {
        print('Creating db ' + dbName + ' ...');
        db._createDatabase(dbName);
        print('Database ' + dbName + ' has been created.');

        users.grantDatabase(user, dbName, 'rw');
        print('RW rights have been granted to ' + user + ' user for db ' + dbName);
    }
}

const parsed_input = parse_inputs(ARGUMENTS);

const user = parsed_input.user;
const password = parsed_input.password;
const dbName = parsed_input.dbName;
const users = require('@arangodb/users');

create_user(users, user, password);
create_grant_db(users, user, dbName);
