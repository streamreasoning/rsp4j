PREFIX : <http://streamreasoning.org/iminds/massif/>

SELECT *
FROM NAMED WINDOW  :win1 [RANGE 5 s, SLIDE 1s] ON STREAM :stream1
FROM NAMED WINDOW  :win2  [RANGE 5 s, SLIDE 1s] ON STREAM :stream2

WHERE  {

    WINDOW ?w {
        ?s ?p ?o
    }

}
