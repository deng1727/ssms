
declare default element namespace "http://www.w3.org/1999/xhtml";
let $b:=
for $a in //a|//link
  let $linkText:=string($a) 
  let $linkURL:=string($a/@href) 
  where not(starts-with($linkURL,'#'))  and not(empty($linkURL))  and string-length($linkURL)>0
return 
   if(contains($linkURL,':'))  then (
      if(starts-with($linkURL,'http') or starts-with($linkURL,'https') or starts-with($linkURL,'HTTP') or starts-with($linkURL,'HTTPS')) then (
        <link linkText="{$linkText}" linkURL="{$linkURL}" />        	
       ) else ('')
   ) else (   
     <link linkText="{$linkText}" linkURL="{$linkURL}" />    
   )  

let $d:=
for $c in //img
 let $linkURL:=string($c/@src)
 where   not(empty($linkURL)) 
return 
  <link linkText="" linkURL="{$linkURL}" />    

let $f:=
for $e in //body[@background]|//table[@background]|//tr[@background]|//td[@background]
let $linkURL:=string($e/@background)
 where   not(empty($linkURL)) 
return 
    <link linkText="" linkURL="{$linkURL}" />    


return 
<links>
  {$b}
  {$d}
  {$f}
</links>