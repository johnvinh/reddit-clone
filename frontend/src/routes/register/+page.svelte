<form method="post" action="/api/user/register">
    <label for="username">Username</label>
    <input id="username" name="username" type="text">

    <label for="password">Password</label>
    <input id="password" name="password" type="password">

    <input name="submit" type="submit">
</form>

<style>
    input[name=submit] {
        display: block;
    }
</style>