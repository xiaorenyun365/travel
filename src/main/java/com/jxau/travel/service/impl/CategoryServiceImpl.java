package com.jxau.travel.service.impl;

import com.jxau.travel.dao.CategoryDao;
import com.jxau.travel.dao.impl.CategoryDaoImpl;
import com.jxau.travel.domain.Category;
import com.jxau.travel.service.CategoryService;

import java.util.List;

/**
 * @Auther: xiaory
 * @Date: 2020/9/15 - 17:21
 */
public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
        /*//1.从redis中查询
        Jedis jedis = JedisUtil.getJedis(); //  获取jedis客户端
        //使用sortedset按顺序查询，从jedis中获取全部类别
        Set<String> categorys = jedis.zrange("category", 0, -1);

        List<Category> cs = null;

        if (categorys == null || categorys.size() == 0){    //jedis中没有数据，从数据库查询，查到后并添加到jedis中
            System.out.println("从数据库中查询..");
            cs = categoryDao.findAll();
            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        }else { //jedis中有数据，但是是set集合，要转为list集合
            System.out.println("从redis中查询..");
            cs = new ArrayList<Category>();
            for (String name : categorys){
                Category category = new Category();
                category.setCname(name);
                cs.add(category);
            }
        }

        return cs;*/

       /* //1.从redis中查询
        //1.1获取jedis客户端
        Jedis jedis = JedisUtil.getJedis();
        //1.2使用sortedset排序查询
        Set<String> categorys = jedis.zrange("category", 0, -1);//0到-1,查询全部

        //2.判断查询的集合是否为空
        List<Category> cs = null;
        if (categorys == null || categorys.size() == 0) {
            //3.如果为空，需要从数据库查询，再将数据存入redis
            System.out.println("从数据库查询。。。");
            //3.1从数据库查询
            cs = categoryDao.findAll();
            //3.2将集合数据存储到redis中的名为“category”的key
            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category", cs.get(i).getCid(), cs.get(i).getCname());
            }
        } else {
            //4.如果不为空，将set数据存入list（因为我们返回要求的格式是list）
            System.out.println("从redis中查询。。。");
            cs = new ArrayList<Category>();
            for (String name : categorys) {
                Category category = new Category();
                category.setCname(name);
                cs.add(category);
            }
        }
        return cs;//无论有无缓存，都要返回cs*/
    }
}
