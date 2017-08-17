



使用SharedPreference保存对象，

```
/**
 * 保存到SharedPreferences
 *
 * @param user
 */
private void saveUser(String preferenceName,String key,User user) {
    SharedPreferences.Editor editor = getSharedPreferences(preferenceName, MODE_PRIVATE).edit();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(user);
        String temp = new String(Base64.encode(baos.toByteArray(),Base64.DEFAULT));
        editor.putString(key,temp);
        editor.apply();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```



从SharedPreferences中得到对象：

```
private User getUserFromCache() {
    User user = null;
    SharedPreferences sp =  getSharedPreferences(User.USER_SHARED_PREFERENCE,MODE_PRIVATE);
    String temp = sp.getString(User.USER, null);
    ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(temp.getBytes(), Base64.DEFAULT));
    try {
        ObjectInputStream ois = new ObjectInputStream(bais);
        user = (User) ois.readObject();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    return user;
}
```